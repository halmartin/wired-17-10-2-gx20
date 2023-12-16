DESCRIPTION = "USB_ModeSwitch"
HOMEPAGE = "http://www.draisberghof.de/usb_modeswitch/"
LICENSE = "GPLv2"
LIC_FILES_CHKSUM = "file://COPYING;md5=94d55d512a9ba36caa9b7df079bae19f"

SRC_URI = "https://dl.meraki.net/${PN}-${PV}.tar.bz2 \
"

DEPENDS = "libusb-compat"

SRC_URI[md5sum] = "c393603908eceab95444c5bde790f6f0"
SRC_URI[sha256sum] = "ce47a3dec3e4c93e0a2fcea64278d0e289e6e78d8e1381c54f03986e443ab90f"

do_configure() {
    :
}

do_compile() {
    ${CC} ${TARGET_CFLAGS} \
        -lusb \
        ${TARGET_LDFLAGS} \
        -o ${B}/usb-modeswitch \
        ${S}/usb_modeswitch.c
}

do_install() {
    install -d -m0755 ${D}/usr/bin
    install ${B}/usb-modeswitch ${D}/usr/bin
}
