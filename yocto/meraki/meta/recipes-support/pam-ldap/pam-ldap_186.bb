SUMMARY = "A PAM plugin to authenticate through LDAP"
HOMEPAGE = "https://www.padl.com/OSS/pam_ldap.html"
LICENSE = "LGPLv2+"
LIC_FILES_CHKSUM = "file://pam_ldap.c;beginline=6;endline=89;md5=49e17ca8eda86502a67b8a4551394040"

DEPENDS += "libpam cyrus-sasl openssl openldap"

REAL_PN := "${@d.getVar("PN", True).replace("-", "_")}"

SRC_URI = "https://dl.meraki.net/${REAL_PN}-${PV}.tar.gz \
           file://1000-perl-5.24.patch \
           file://1001-add-fipsmode.patch \
          "
SRC_URI[md5sum] = "58c8689921c5c4578363438acd8503c2"
SRC_URI[sha256sum] = "c8fe22d559d5a3f528b24eb97175085f188c0f11ade337a868590ac0e0716453"

CACHED_CONFIGUREVARS = "LIBS='-lsasl2 -lssl -lcrypto'"

S = "${WORKDIR}/${REAL_PN}-${PV}"

inherit autotools-brokensep

do_install() {
    install -m0755 -d ${D}${base_libdir}/security
    install ${B}/pam_ldap.so ${D}${base_libdir}/security
}

FILES_${PN} = "${base_libdir}/security/pam_ldap.so"
