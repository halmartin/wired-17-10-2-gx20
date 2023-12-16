DESCRIPTION = "Lasso is a free software C library aiming to implement the Liberty Alliance standards; it defines processes for federated identities, single sign-on and related protocols."
LICENSE = "GPLv2"
SECTION = "connectivity"

DEPENDS += "zlib glib-2.0 libxml2 libxslt xmlsec1 openssl python3-six-native"

LIC_FILES_CHKSUM = "file://COPYING;md5=612930cf2341d24b7faaa42a4bde6062"

SRC_URI = "https://dl.meraki.net/${PN}-${PV}.tar.gz \
           file://0001-Remove-hardcode-library-path-to-cross-compile.patch \
           "

SRC_URI[md5sum] = "f943f3ed67fabad11c6bad1ab615398f"
SRC_URI[sha256sum] = "be105c8d400ddeb798419eafa9522101d0f63dc42b79b7131b6010c4a5fc2058"

inherit autotools pkgconfig python3native

PACKAGECONFIG ??= ""
PACKAGECONFIG[perl] = ",--disable-perl,"
PACKAGECONFIG[python] = ",--disable-python,"
