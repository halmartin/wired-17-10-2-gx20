# build everything into haveged, avoid building libhavege
EXTRA_OECONF += "--enable-static --disable-shared"

# shrink it down more by disabling self tests
EXTRA_OECONF += "--disable-olt --disable-nistest"

# move binaries from /usr/sbin to /usr/bin
do_install_append() {
    mv -T ${D}/${sbindir} ${D}/${bindir}
}
