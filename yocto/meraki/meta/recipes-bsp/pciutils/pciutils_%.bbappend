# Main package should not pull in pci.ids.gz
RDEPENDS_${PN}_remove = "${PN}-ids"
