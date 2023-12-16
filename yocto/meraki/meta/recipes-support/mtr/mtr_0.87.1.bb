SUMMARY = "Combined traceroute and ping utility"
DESCRIPTION = "mtr combines the functionality of the 'traceroute' and 'ping' programs in a single network diagnostic tool."
HOMEPAGE = "http://www.bitwizard.nl/mtr/"
SECTION = "net"
DEPENDS = "ncurses"
RDEPENDS_${PN} = "ncurses-terminfo-base"

LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=0636e73ff0215e8d672dc4c32c317bb3 \
                    file://mtr.c;beginline=5;endline=16;md5=af1fafbbfa1bfd48af839f4bb3221106"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI = " \
    https://dl.meraki.net/${PN}-${PV}.tar.gz \
    file://900-meraki-json-output.patch \
"

SRC_URI[md5sum] = "2f996a806764b6b7dbc7ee8e8f8cdc28"

inherit autotools pkgconfig

EXTRA_OECONF = "--without-gtk"

PACKAGECONFIG ??= "${@bb.utils.filter('DISTRO_FEATURES', 'ipv6', d)}"
PACKAGECONFIG[ipv6] = "--enable-ipv6,--disable-ipv6,"

PACKAGES += "${PN}-bash-completions"

FILES_${PN}-bash-completions = "${datadir}/bash-completion/"
