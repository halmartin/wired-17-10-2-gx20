FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += " \
    file://100-disable-udp-checksums.patch \
    file://200-bind-addr.patch \
    "
