SHELL := /bin/bash
IMAGE := core-image-custom

all default: build

distclean: clean
	rm -rf downloads

clean:
	rm -rf cache/ bitbake-cookerdaemon.log sstate-cache/ tmp

build:
	source submodules/poky/oe-init-build-env $(abspath .) && \
	bitbake $(IMAGE)

shell:
	source submodules/poky/oe-init-build-env $(abspath .) && \
	$(SHELL)

launch:
	source submodules/poky/oe-init-build-env $(abspath .) && \
	runqemu qemux86-64 $(IMAGE) nographic serial kvm

launch_full:
	source submodules/poky/oe-init-build-env $(abspath .) && \
	runqemu qemux86-64 $(IMAGE)
