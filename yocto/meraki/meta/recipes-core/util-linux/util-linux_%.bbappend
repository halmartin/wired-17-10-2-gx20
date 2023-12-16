# This dependency should be safe to re-add in sumo and beyond. We just need
# 29f65bda6d2c9f
# "nativesdk-glibc: Split glibc and libcrypt to use libxcrypt instead"
DEPENDS_remove = "virtual/crypt"

EXTRA_OECONF_append_class-target = " --without-ncurses \
                 --without-python \
                 --without-udev \
                 --disable-ul \
                 "

PACKAGECONFIG_class-target  ?= ""

PACKAGES_prepend_class-target = "util-linux-nsenter util-linux-unshare "

FILES_util-linux-nsenter = "${bindir}/nsenter"
FILES_util-linux-unshare = "${bindir}/unshare"

# Upstream adds --without-readline which configure doesn't recognize and I
# don't want to polute the logs so I am just emptying the associated
# PACKAGECONFIG.
PACKAGECONIG[readline] = ""
