LICENSE = "MIT"
LIC_FILES_CHKSUM = "file://${COMMON_LICENSE_DIR}/MIT;md5=0835ade698e0bcf8506ecda2f7b4f302"

#DEPENDS = "rsync-native"

python __anonymous() {
    unused_tasks = [
        "build", "checkuri", "clean", "cleanall", "cleansstate", "compile",
        "configure", "deploy_source_date_epoch",
        "deploy_source_date_epoch_setscene", "devshell", "fetch", "install",
        "package", "packagedata", "packagedata_setscene", "package_qa",
        "package_qa_setscene", "package_setscene",
        "package_write_ipk", "package_write_ipk_setscene", "patch",
        "populate_lic", "populate_lic_setscene", "populate_sysroot",
        "populate_sysroot_setscene", "prepare_recipe_sysroot", "pydevshell",
        "unpack"
    ]
    for task in unused_tasks:
        bb.build.deltask(f"do_{task}", d)
}

do_nothing() {
    echo "Nothing to see here" >&2
    exit 65
}

addtask do_nothing
do_nothing[nostamp] = "1"
do_nothing[doc] = "This is a custom task that does nothing."
do_nothing[depends] += "rsync-native:do_populate_sysroot"
