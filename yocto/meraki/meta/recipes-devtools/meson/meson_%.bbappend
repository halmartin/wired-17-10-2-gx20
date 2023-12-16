FILESEXTRAPATHS_prepend := "${THISDIR}/${PN}:"

# This package wasn't split out of `python3-setuptools` until dunfell.
# Therefore, we just remove it until we've successfully bumped to dunfell.
RDEPENDS_${PN}_remove = "python3-pkg-resources"
