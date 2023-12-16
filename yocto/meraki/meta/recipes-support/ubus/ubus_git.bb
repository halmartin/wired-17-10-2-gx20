DESCRIPTION = "OpenWrt RPC daemon"
HOMEPAGE = "http://wiki.openwrt.org/doc/techref/ubus"
LICENSE = "LGPLv2"
LIC_FILES_CHKSUM = "file://ubusd.c;beginline=1;endline=12;md5=1b6a7aecd35bdd25de35da967668485d"

SRC_URI = "\
    git://git.openwrt.org/project/ubus.git;branch=master \
    file://0001-version-libraries.patch \
    file://fix-dirs.patch \
    "
SRCREV = "221ce7e7ff1bd1a0c9995fa9d32f58e865f7207f"
S = "${WORKDIR}/git"

inherit cmake

EXTRA_OECMAKE="-DBUILD_LUA=OFF -DBUILD_EXAMPLES=OFF"

DEPENDS = "json-c libubox"
