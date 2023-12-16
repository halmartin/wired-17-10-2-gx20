DESCRIPTION = "Basic utility library"
HOMEPAGE = "http://wiki.openwrt.org/doc/uci"
LICENSE = "BSD-1-Clause&BSD-3-Clause"
LIC_FILES_CHKSUM = "file://uloop.c;beginline=4;endline=17;md5=8e5f24f89cfa74e3ae9e49b29cc7b25c"

SRC_URI = "\
    git://git.openwrt.org/project/libubox.git;branch=master \
    file://0001-version-libraries.patch \
    file://fix-libdir.patch \
    "
SRCREV = "eeef7b50a06bc3c3218d560b4b513b4e7b19127f"
S = "${WORKDIR}/git"

inherit cmake

DEPENDS = "json-c"

EXTRA_OECMAKE="-DBUILD_LUA=OFF -DBUILD_EXAMPLES=OFF"
