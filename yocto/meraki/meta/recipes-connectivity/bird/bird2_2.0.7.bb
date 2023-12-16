DESCRIPTION = "Dynamic routing daeomon"
HOMEPAGE = "http://bird.network.cz/"
LICENSE = "GPLv2"
SECTION = "connectivity"
LIC_FILES_CHKSUM = "file://README;beginline=77;endline=93;md5=0efecc23f039022580d0bac9a52f6117"

# The tarball is named 'bird' rather than 'bird2'
# in dl.meraki.net and network.cz
SRC_URI = "ftp://bird.network.cz/pub/bird/bird-${PV}.tar.gz"
SRC_URI[md5sum] = "dc884bbe5905578e452f28158700527c"
SRC_URI[sha256sum] = "631d2b58aebdbd651aaa3c68c3756c02ebfe5b1e60d307771ea909eeaa5b1066"

FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}2:"
SRC_URI += "file://0001-Rename-bird.ctl-to-bird2.ctl.patch \
            file://0002-bird2-cli-export-route-state-json.patch \
            file://0003-bird2-Add-event-logging-mechanism.patch \
            file://0004-bird2-Event-logging-for-BGP.patch \
            file://0005-add-flock-when-reading-config-file.patch \
           "

S = "${WORKDIR}/bird-${PV}"

inherit autotools externalsrc pkgconfig

DEPENDS += "bison-native flex-native"

# Excluding aclocal prevents removing aclocal.m4 (which is custom for bird and
# can't be removed.  Excluding autoheader prevents the "autoheader failed with
# exit code 1" error.
EXTRA_AUTORECONF += "--exclude=aclocal --exclude=autoheader"
EXTRA_OECONF += "--with-protocols=bgp,ospf,static,pipe --disable-client"

do_install_append() {
  mv ${D}${sbindir}/bird ${D}${sbindir}/bird2
  mv ${D}${sbindir}/birdcl ${D}${sbindir}/birdcl2
  ln -s ${sbindir}/birdcl2 ${D}${sbindir}/birdc2
  rm -rf ${D}/var
}

PACKAGE_BEFORE_PN += "${PN}-client"
FILES_${PN}-client += " \
    /usr/sbin/birdc2 \
    /usr/sbin/birdcl2 \
"
