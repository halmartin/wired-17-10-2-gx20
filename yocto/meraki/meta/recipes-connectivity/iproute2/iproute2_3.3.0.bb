DESCRIPTION = "Iproute2 is a collection of utilities for controlling TCP / IP networking and traffic control in Linux."
LICENSE = "GPLv2"
SECTION = "connectivity"

LIC_FILES_CHKSUM = "file://COPYING;md5=eb723b61539feef013de476e68b5c50a"

SRC_URI = "https://dl.meraki.net/${BP}.tar.xz"
SRC_URI[md5sum] = "39a7ff2c5149475c710365954e2ce49b"

SRC_URI += " \
    file://001-iproute2-2.6.11_Config.patch \
    file://002-iproute2-ipxfrm_no_sctp.patch \
    file://004-darwin_fixes.patch \
    file://005-flex-generated.patch \
    file://007-version_includes.patch \
    file://100-allow_pfifo_fast.patch \
    file://110-extra-ccopts.patch \
    file://120-libnetlink-pic.patch \
    file://200-act_connmark.patch \
    file://500-musl.patch \
    "

TARGET_CFLAGS += "-D_GNU_SOURCE -DRESOLVE_HOSTNAMES"
EXTRA_OEMAKE  += "HOSTCC='${BUILD_CC}' CC='${CC}' EXTRA_CCOPTS='${TARGET_CFLAGS}'"

do_configure() {
    # For now disable compiling of the misc directory because it seems to fail
    echo 'all:' > ${S}/misc/Makefile
    echo "IP_CONFIG_SETNS:=y" >> Config
}

do_compile() {
    oe_runmake -C netem
    oe_runmake
}

do_install() {
    install -D -m 0755 ip/ip ${D}/${sbindir}/ip
    install -D -m 0755 tc/tc ${D}/${sbindir}/tc
}

PACKAGES =+ "${PN}-ip ${PN}-tc"
FILES_${PN} = "${sbindir}/ip"
FILES_${PN}-tc = "${sbindir}/tc"
