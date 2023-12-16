SECTION = "base"
SUMMARY = "Library providing support for "XML Signature" and "XML Encryption" standards"

HOMEPAGE = "https://www.aleksey.com/xmlsec"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://Copyright;md5=32e47e213c77c55d2c666351d7ce16b5"

SRC_URI = "https://dl.meraki.net/${BP}.tar.gz"
SRC_URI[md5sum] = "ce12af00283eb90d9281956524250d6e"
SRC_URI[sha256sum] = "3221593ca50f362b546a0888a1431ad24be1470f96b2469c0e0df5e1c55e7305"

inherit autotools pkgconfig

DEPENDS = "libxml2 openssl libtool"
RDEPENDS_${PN} = "openssl libxml2"

SOLIBS = ".so*"
FILES_SOLIBSDEV = ""
INSANE_SKIP_${PN} = "dev-so"

EXTRA_OECONF = "--without-libxslt \
                --without-gcrypt \
                --without-gnutls \
                --without-nss \
                "

do_configure() {
    oe_runconf
}

do_install_append() {
    rm ${D}/usr/lib/xmlsec1Conf.sh
    lnr ${D}/usr/lib/libxmlsec1-openssl.so ./libxmlsec1-openssl.so.1.2.20
}

ERROR_QA_remove = "install-host-path"
WARN_QA += "install-host-path"
