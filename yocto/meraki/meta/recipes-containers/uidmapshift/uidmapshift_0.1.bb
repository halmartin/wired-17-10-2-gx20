DESCRIPTION = "Utility to remap uid/gid of a container rootfs"
HOMEPAGE = "https://bazaar.launchpad.net/~serge-hallyn/+junk/nsexec/view/head:/uidmapshift.c"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://uidmapshift.c;beginline=1;endline=11;md5=3e6af10f4110412d72e92c0398b8c05d"

FILESEXTRAPATHS_prepend := "${THISDIR}/${BPN}:"
SRC_URI = "file://uidmapshift.c;subdir=${S}"

do_compile() {
    ${CC} ${CPPFLAGS} ${CFLAGS} uidmapshift.c -o uidmapshift ${LDFLAGS}
}

do_install() {
    install -d ${D}/${sbindir}
    install -m 0755 uidmapshift ${D}/${sbindir}
}
