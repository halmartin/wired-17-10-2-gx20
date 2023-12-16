# Move lsusb to /usr/sbin because a lot of our code has hard-coded references
# to /usr/sbin/lsusb.
do_install_append() {
    mkdir -p ${D}${sbindir}
    mv ${D}${bindir}/lsusb ${D}${sbindir}
}

# Move these files into the -extra package. We don't need them in the main package.
PACKAGES += "${PN}-extra"
FILES_${PN}-extra += "${bindir}/usb-devices"
FILES_${PN}-extra += "${bindir}/lsusb.py"
FILES_${PN}-extra += "${bindir}/usbhid-dump"

FILES_${PN} = "${sbindir}/lsusb"
