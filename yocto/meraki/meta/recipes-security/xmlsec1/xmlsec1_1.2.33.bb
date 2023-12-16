SECTION = "base"
SUMMARY = "Library providing support for "XML Signature" and "XML Encryption" standards"

HOMEPAGE = "https://www.aleksey.com/xmlsec"
LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://Copyright;md5=7bd5ec73d9917fedbc45d754de728e7a"

SRC_URI = "https://dl.meraki.net/${BP}.tar.gz"
SRC_URI[md5sum] = "e3f3f7940ee1e25c222d49b5fdff9967"
SRC_URI[sha256sum] = "26041d35a20a245ed5a2fb9ee075f10825664d274220cb5190340fa87a4d0931"

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

do_install_append() {
    rm ${D}/usr/lib/xmlsec1Conf.sh
    lnr ${D}/usr/lib/libxmlsec1-openssl.so ./libxmlsec1-openssl.so.1.2.33
}

ERROR_QA_remove = "install-host-path"
WARN_QA += "install-host-path"
