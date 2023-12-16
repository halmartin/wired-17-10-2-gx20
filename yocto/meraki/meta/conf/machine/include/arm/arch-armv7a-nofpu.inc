require conf/machine/include/arm/arch-armv7a.inc

# Do not use floating point support at all
DEFAULTTUNE = "armv7a"
TUNE_FEATURES_remove = "vfp"
