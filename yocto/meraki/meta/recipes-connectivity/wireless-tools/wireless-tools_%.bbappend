FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://debian-2.patch \
    file://qca-extend-iwpriv-ioctl.patch \
    "

do_install_append() {
    # Move all binaries from /sbin to /usr/sbin
    mkdir -p ${D}/${sbindir}
    mv ${D}/sbin/* ${D}/${sbindir}/
    rmdir ${D}/sbin
}
