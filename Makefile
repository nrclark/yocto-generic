clean:
	rm -rf cache/ bitbake-cookerdaemon.log sstate-cache/ tmp

build:
	. submodules/poky/oe-init-build-env $(abspath .) && \
	bitbake core-image-minimal
