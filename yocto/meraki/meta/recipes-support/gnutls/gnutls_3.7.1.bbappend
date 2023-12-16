# gnutls depends on gnulib which is changed from
# gnu99 to gnu11 between 3.6.13 and 3.6.14.
# we need the following workaround for wired-arm-nsp 
# which use old gcc 4.x
CFLAGS += "-std=gnu99"

