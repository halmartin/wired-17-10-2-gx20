DESCRIPTION = "mtd utility from OpenWrt"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://../mtd.c;beginline=7;endline=19;md5=b830b1f037adb0029aba0aa10dac1060"

FILESEXTRAPATHS =. "${ROUTER_ROOT}/openwrt/package/mtd:"
SRC_URI = "file://mtd.c"

do_compile() {
    ${CC} ${CFLAGS} ${LDFLAGS} ${WORKDIR}/mtd.c -o mtd
}

do_install() {
    install -Dm0755 mtd ${D}/${base_sbindir}/mtd
}
