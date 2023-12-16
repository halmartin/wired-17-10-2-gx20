FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0100-libtool-install.patch \
            file://0200-remove-lutil-memcmp.patch \
            file://0500-cross-compile.patch \
            file://0750-no-strip.patch \
            file://0900-add-fipsmode.patch \
            "
EXTRA_OECONF += "--enable-shared \
                 --disable-static \
                 --disable-debug \
                 --enable-dynamic \
                 --enable-syslog \
                 --disable-local \
                 --without-fetch \
                 --with-threads \
                 --with-yielding_select=yes \
"

PACKAGECONFIG_append += " openssl sasl "
PACKAGECONFIG_remove += " gnutls "
