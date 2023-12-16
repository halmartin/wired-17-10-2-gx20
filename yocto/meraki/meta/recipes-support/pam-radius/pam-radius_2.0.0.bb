SUMMARY = "A PAM plugin to authenticate through Radius"
HOMEPAGE = "https://github.com/FreeRADIUS/pam_radius"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://LICENSE;md5=cbbd794e2a0a289b9dfcc9f513d1996e"

DEPENDS = "libpam"

SRC_URI = "https://dl.meraki.net/${PN}-${PV}.tar.gz"
SRC_URI[md5sum] = "763d921dc4a455c973b6f7fbd7932a91"
SRC_URI[sha256sum] = "798dc358e7a143163e103bd38c4101645b2e93d82aa0ec63d32c0cb929d9c65c"

REAL_PN := "${@d.getVar("PN", True).replace("-", "_")}"
REAL_PV := "${@d.getVar("PV", True).replace(".", "_")}"
S = "${WORKDIR}/${REAL_PN}-release_${REAL_PV}"

inherit autotools-brokensep

do_install() {
    install -m0755 -d ${D}${base_libdir}/security
    install ${B}/pam_radius_auth.so ${D}${base_libdir}/security
}

FILES_${PN} = "${base_libdir}/security/pam_radius_auth.so"
