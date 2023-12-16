# Added for python backported from Yocto Dunfell while we're still on Rocko.
# Virtual/crypt is added in Thud, so once we upgrade that far this
# line should be removed.
DEPENDS_remove += "virtual/crypt"

# In our version of Yocto ldconfig is a glibc module instead of
# a separate package as in Yocto/Dunfell. This can be removed if
# we upgrade to Dunfell
RDEPENDS_${PN}-ctypes_remove_libc-glibc += "${MLPREFIX}ldconfig"
