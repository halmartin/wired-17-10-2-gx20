FILESEXTRAPATHS_prepend := "${THISDIR}/kconfig:"

SRC_URI += " \
        file://defconfig \
"

# dts exists in external source (linux-3.14)

COMPATIBLE_MACHINE = "wired-arm-qca"
