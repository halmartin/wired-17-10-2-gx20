SUMMARY = "libqmi is a library for talking to WWAN devices by QMI protocol"
DESCRIPTION = "libqmi is a glib-based library for talking to WWAN modems and \
               devices which speak the Qualcomm MSM Interface (QMI) protocol"
HOMEPAGE = "http://www.freedesktop.org/wiki/Software/libqmi"
LICENSE = "GPLv2 & LGPLv2.1"
LIC_FILES_CHKSUM = " \
    file://COPYING;md5=b234ee4d69f5fce4486a80fdaf4a4263 \
    file://COPYING.LIB;md5=4fbd65380cdd255951079008b364516c \
"

DEPENDS = "glib-2.0 glib-2.0-native autoconf-archive-native"

inherit autotools pkgconfig bash-completion gobject-introspection

SRC_URI = "http://www.freedesktop.org/software/${BPN}/${BPN}-${PV}.tar.xz"

SRC_URI[sha256sum] = "c0adcb84ba452e3e13edeb3f94f7b003ea927b354bc187629e05efb333ce270b"

PACKAGECONFIG ??= "udev mbim"
PACKAGECONFIG[udev] = ",--without-udev,libgudev"
PACKAGECONFIG[mbim] = "--enable-mbim-qmux,--disable-mbim-qmux,libmbim"
