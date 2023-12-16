DESCRIPTION = "A command-line oriented TCP/IP packet assembler/analyzer."
LICENSE = "GPLv2"
SECTION = "network"
LIC_FILES_CHKSUM = "file://COPYING;md5=3728a6c4c9630a9e796ad4b82dacd887"

SRC_URI = "https://dl.meraki.net/${BPN}-${PV}.tar.gz"
SRC_URI[md5sum] = "ca4ea4e34bcc2162aedf25df8b2d1747"
SRC_URI[sha256sum] = "f5a671a62a11dc8114fa98eade19542ed1c3aa3c832b0e572ca0eb1a5a4faee8"

DEPENDS += "libpcap"

SRC_URI += " \
    file://100-makefile.patch \
    file://101-endianness.patch \
    file://102-lib_rename.patch \
    file://104-byteorder.patch \
    file://105-makefile-in.patch \
    file://106-patch-fix-memcpy-overflow-bug.patch \
    "

do_configure_append() {
    ./configure --no-tcl
}

do_compile() {
    # remove -L/usr/local/lib from linker flags
    sed -i -e 's| -L/usr/local/lib | |g' Makefile
    oe_runmake \
        CC="${CC}" \
        CCOPT="${CFLAGS} ${LDFLAGS}" \
        all
}

do_install() {
    install -D -m0755 hping3 ${D}/${bindir}/hping3
}
