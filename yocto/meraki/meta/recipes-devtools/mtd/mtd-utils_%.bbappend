FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"
SRC_URI += "file://003-fix-oob-semantics.patch"

# Disable LZO support
DEPENDS_remove = "lzo"
EXTRA_OECONF += "--without-lzo"

# The alternatives system creates absolute symlinks; in this case there are no
# other alternatives so instead of flash_eraseall -> flash_eraseall.mtd-utils
# we have more simply just flash_eraseall. Note that this might have to be
# revisited if the flash_eraseall from busybox is ever enabled.
ALTERNATIVE_${PN}_remove = "flash_eraseall"
