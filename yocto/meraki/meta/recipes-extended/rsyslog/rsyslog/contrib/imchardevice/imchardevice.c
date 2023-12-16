/* Click Char Device Input Module
 *
 * This rsyslog module is for reading click logs data
 * Module is based on imkmg module so it retains its structure
 * and other part is currently in chardevice.c file instead of this (imchardevice.c)
 * For more information see that file.
 *
 * Copyright (C) 2008-2014 Adiscon GmbH, 2016 Meraki Inc
 *
 * This file is part of rsyslog.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 *       -or-
 *       see COPYING.ASL20 in the source distribution
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
#include "config.h"
#include "rsyslog.h"
#include <stdio.h>
#include <assert.h>
#include <string.h>
#include <stdarg.h>
#include <ctype.h>
#include <stdlib.h>
#include <sys/socket.h>

#include "dirty.h"
#include "cfsysline.h"
#include "obj.h"
#include "msg.h"
#include "module-template.h"
#include "datetime.h"
#include "imchardevice.h"
#include "net.h"
#include "glbl.h"
#include "prop.h"
#include "unicode-helper.h"

MODULE_TYPE_INPUT
MODULE_TYPE_NOKEEP
MODULE_CNFNAME("imchardevice")

/* Module static data */
DEF_IMOD_STATIC_DATA
DEFobjCurrIf(datetime)
DEFobjCurrIf(glbl)
DEFobjCurrIf(prop)
DEFobjCurrIf(net)

/* config settings */
typedef struct configSettings_s {
	int iFacilIntMsg; /* the facility to use for internal messages (set by driver) */
} configSettings_t;
static configSettings_t cs;

static modConfData_t *loadModConf = NULL;/* modConf ptr to use for the current load process */
static modConfData_t *runModConf = NULL;/* modConf ptr to use for the current load process */
static int bLegacyCnfModGlobalsPermitted;/* are legacy module-global config parameters permitted? */

static prop_t *pInputName = NULL;	/* there is only one global inputName for all messages generated by this module */
static prop_t *pLocalHostIP = NULL;	/* a pseudo-constant propterty for 127.0.0.1 */

static inline void
initConfigSettings(void)
{
	cs.iFacilIntMsg = charDeviceFacilIntMsg();
}


/* enqueue the the kernel message into the message queue.
 * The provided msg string is not freed - thus must be done
 * by the caller.
 * rgerhards, 2008-04-12
 */
static rsRetVal
enqMsg(uchar *msg)
{
	smsg_t *pMsg;
	DEFiRet;

	assert(msg != NULL);

	CHKiRet(msgConstruct(&pMsg));

	MsgSetFlowControlType(pMsg, eFLOWCTL_LIGHT_DELAY);
	MsgSetInputName(pMsg, pInputName);
	MsgSetRawMsgWOSize(pMsg, (char*)msg);
	pMsg->msgFlags  = NEEDS_PARSING | PARSE_HOSTNAME | NEEDS_DNSRESOL;
	MsgSetRcvFrom(pMsg, glbl.GetLocalHostNameProp());
	MsgSetRcvFromIP(pMsg, pLocalHostIP);

	CHKiRet(submitMsg(pMsg));

finalize_it:
	RETiRet;
}


/* log an imchardevice-internal message
 * rgerhards, 2008-04-14
 */
rsRetVal charDeviceLogIntMsg(syslog_pri_t priority, char *fmt, ...)
{
	DEFiRet;
	va_list ap;
	uchar msgBuf[2048]; /* we use the same size as sysklogd to remain compatible */

	va_start(ap, fmt);
	vsnprintf((char*)msgBuf, sizeof(msgBuf), fmt, ap);
	va_end(ap);

	logmsgInternal(NO_ERRCODE, priority, msgBuf, 0);

	RETiRet;
}


/* log a message from char device
 */
rsRetVal Syslog(uchar *pMsg)
{
	DEFiRet;
	iRet = enqMsg(pMsg);
	RETiRet;
}


BEGINrunInput
CODESTARTrunInput
	/* this is an endless loop - it is terminated when the thread is
	 * signalled to do so. This, however, is handled by the framework,
	 * right into the sleep below.
	 */
	while(!pThrd->bShallStop) {
		/* charDeviceLogMsg() waits for the next log message, obtains it
                 * and then submits it to the rsyslog main queue.
	   	 */
                CHKiRet(charDeviceLogMsg(runModConf));
	}
finalize_it:
ENDrunInput


BEGINbeginCnfLoad
CODESTARTbeginCnfLoad
	loadModConf = pModConf;
	pModConf->pConf = pConf;
	/* init our settings */
	pModConf->iFacilIntMsg = charDeviceFacilIntMsg();
	loadModConf->configSetViaV2Method = 0;
	bLegacyCnfModGlobalsPermitted = 1;
	/* init legacy config vars */
	initConfigSettings();
ENDbeginCnfLoad


BEGINendCnfLoad
CODESTARTendCnfLoad
	if(!loadModConf->configSetViaV2Method) {
		/* persist module-specific settings from legacy config system */
		loadModConf->iFacilIntMsg = cs.iFacilIntMsg;
	}

	loadModConf = NULL; /* done loading */
ENDendCnfLoad


BEGINcheckCnf
CODESTARTcheckCnf
ENDcheckCnf


BEGINactivateCnfPrePrivDrop
CODESTARTactivateCnfPrePrivDrop
	runModConf = pModConf;
        iRet = charDeviceWillRunPrePrivDrop(runModConf);
ENDactivateCnfPrePrivDrop


BEGINactivateCnf
CODESTARTactivateCnf
ENDactivateCnf


BEGINfreeCnf
CODESTARTfreeCnf
ENDfreeCnf


BEGINwillRun
CODESTARTwillRun
        iRet = charDeviceWillRunPostPrivDrop(runModConf);
ENDwillRun


BEGINafterRun
CODESTARTafterRun
        iRet = charDeviceAfterRun(runModConf);
ENDafterRun


BEGINmodExit
CODESTARTmodExit
	if(pInputName != NULL)
		prop.Destruct(&pInputName);
	if(pLocalHostIP != NULL)
		prop.Destruct(&pLocalHostIP);

	/* release objects we used */
	objRelease(glbl, CORE_COMPONENT);
	objRelease(net, CORE_COMPONENT);
	objRelease(datetime, CORE_COMPONENT);
	objRelease(prop, CORE_COMPONENT);
ENDmodExit


BEGINqueryEtryPt
CODESTARTqueryEtryPt
CODEqueryEtryPt_STD_IMOD_QUERIES
CODEqueryEtryPt_STD_CONF2_QUERIES
CODEqueryEtryPt_STD_CONF2_PREPRIVDROP_QUERIES
ENDqueryEtryPt

static rsRetVal resetConfigVariables(uchar __attribute__((unused)) *pp, void __attribute__((unused)) *pVal)
{
	cs.iFacilIntMsg = charDeviceFacilIntMsg();
	return RS_RET_OK;
}

BEGINmodInit()
CODESTARTmodInit
	*ipIFVersProvided = CURR_MOD_IF_VERSION; /* we only support the current interface specification */
CODEmodInit_QueryRegCFSLineHdlr
	CHKiRet(objUse(datetime, CORE_COMPONENT));
	CHKiRet(objUse(glbl, CORE_COMPONENT));
	CHKiRet(objUse(prop, CORE_COMPONENT));
	CHKiRet(objUse(net, CORE_COMPONENT));

	/* we need to create the inputName property (only once during our lifetime) */
	CHKiRet(prop.CreateStringProp(&pInputName, UCHAR_CONSTANT("imchardevice"), sizeof("imchardevice") - 1));
	CHKiRet(prop.CreateStringProp(&pLocalHostIP, UCHAR_CONSTANT("127.0.0.1"), sizeof("127.0.0.1") - 1));

	/* init legacy config settings */
	initConfigSettings();

	CHKiRet(omsdRegCFSLineHdlr((uchar *)"resetconfigvariables", 1, eCmdHdlrCustomHandler,
			resetConfigVariables, NULL, STD_LOADABLE_MODULE_ID));
ENDmodInit
/* vim:set ai:
 */
