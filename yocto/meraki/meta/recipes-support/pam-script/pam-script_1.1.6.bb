SUMMARY = "A PAM plugin to authenticate through scripts"
HOMEPAGE = "https://github.com/jeroennijhof/pam_script"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=d8e20eece214df8ef953ed5857862150"

DEPENDS = "libpam"

SRC_URI = "https://dl.meraki.net/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "99a1f03078926096cddc73bd17462eb0"
SRC_URI[sha256sum] = "d7cb2fadf8db8fcd099a7a7151ec1b018e79f110af79955df0e0081ea3a93e1d"

inherit autotools

do_install() {
    install -m0755 -d ${D}/usr/lib/security
    install ${B}/.libs/pam_script.so ${D}/usr/lib/security
}

FILES_${PN} = "/usr/lib/security/pam_script.so"
