DESCRIPTION = "Lasso is a free software C library aiming to implement the Liberty Alliance standards; it defines processes for federated identities, single sign-on and related protocols."
LICENSE = "GPLv2"
SECTION = "connectivity"
HOMEPAGE = "https://dev.entrouvert.org/releases/lasso"

DEPENDS += "zlib glib-2.0 libxml2 libxslt xmlsec1 openssl python3-six-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=612930cf2341d24b7faaa42a4bde6062"

SRC_URI = "https://dl.meraki.net/${PN}-${PV}.tar.gz \
           file://0001-lasso_2.7.0-Remove-hardcode-library-path.patch \
           "

SRC_URI[md5sum] = "8262e05f7844aea6b32239bff6ddd6b9"
SRC_URI[sha256sum] = "9282f2a546ee84b6d3a8236970fea3a47bea51cb247c31a05a374c22eb451d8d"

export STAGING_INCDIR
export STAGING_LIBDIR

inherit autotools pkgconfig python3native

PACKAGECONFIG ??= ""
PACKAGECONFIG[perl] = ",--disable-perl,"
PACKAGECONFIG[python] = ",--disable-python,"
