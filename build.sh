#!/bin/bash -e

CURRENT_DIR=$PWD
trap "{ E_CODE=$?; cd "$CURRENT_DIR"; exit $E_CODE; }" SIGINT SIGTERM ERR EXIT

cd `dirname "$0"`
BUILD_ROOT="$PWD"
META_MERAKI_ROOT="$BUILD_ROOT/yocto/meraki"
YOCTO_META_ROOT="$BUILD_ROOT/yocto"
ROUTER_ROOT=$(readlink -f "$YOCTO_META_ROOT/..")

cd "$YOCTO_META_ROOT"

if [ ! -d "./poky" ]; then
    echo "Cloning Yocto Pocky..."
    git clone https://git.yoctoproject.org/git/poky -b rocko-18.0.3
fi

if [ ! -d "./meta-openembedded" ]; then
    echo "Cloning Yocto meta-openembedded..."
    git clone https://github.com/openembedded/meta-openembedded.git -b rocko
fi	

if [ ! -d "$ROUTER_ROOT/kernel-sources/.git" ]; then
    (cd $ROUTER_ROOT/kernel-sources && \
        git init && \
        git config user.name "James Bond" && \
        git config user.email "007@sis.gov.uk" && \
        git add . && \
        git commit -m "initial commit" && \
        git checkout -b w17 && \
        git checkout master)
fi

if ! [ -e "$YOCTO_META_ROOT/meraki" ] || \
   ! [ -e "$YOCTO_META_ROOT/poky" ] || \
   ! [ -e "$YOCTO_META_ROOT/meta-openembedded" ]; then
    echo "    $YOCTO_META_ROOT/poky"
    echo "    $YOCTO_META_ROOT/meta-openembedded"
    echo "Please check that these directories exist."
    echo "See README for details."
    return 1
fi

TEMPLATECONF="$YOCTO_META_ROOT/meraki/meta/conf" \
    source "$YOCTO_META_ROOT/poky/oe-init-build-env" build

sed -i -e "s|##ROUTERROOT##|$ROUTER_ROOT|g" \
    "$BUILDDIR/conf/local.conf"

MACHINE=wired-arm-qca bitbake oss-image || exit 1


