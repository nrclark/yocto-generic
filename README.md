# Introduction #

This repo holds a sample Yocto project that is intended to be a starting-point
for a POC around a Yocto-based build.

# Usage #

Right now, this repo's tooling is pretty small. The default `build` operation
generates an x86-64 VM image targeting QEMU. The `launch` operation runs the
VM and presents a serial console.

# Dependencies #

In order to build this repo, you only need to install Yocto's build-time
dependencies. On Ubuntu, this can be done using a convenient Makefile helper
I added like this: `sudo apt install $(make showdeps)`.
