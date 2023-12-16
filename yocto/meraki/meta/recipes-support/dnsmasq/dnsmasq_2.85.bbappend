FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

SRC_URI += "file://0001-fix-dhcp-no-address-warning.patch \
            file://0002-opendns-adult-block.patch \
            "

# shrink it down by disabling TFTP/DHCP/inotify
# note: this overrides COPTS defined in main recipe
EXTRA_OEMAKE += "COPTS='-DNO_TFTP -DNO_DHCP -DNO_INOTIFY'"

# move binaries from /usr/bin to /usr/sbin
do_install_append() {
    mv -T ${D}/${bindir} ${D}/${sbindir}
}
