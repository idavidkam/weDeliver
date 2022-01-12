package app.shipcalc

data class Package(
    var packageType: PackageTypesEnum? = null,
    var isFragile: Boolean? = null,
    var weight: Double? = null,
    var coordinate: Coordinate? = null,
    var name: String? =null,
    var address: String? =null,
    var status: PackageStatusEnum? = null,
    var id: String? = null,
    var deliveryGuy: String? = null
) {
    /*var id : String = ""
        get() = field

    var status: PackageStatusEnum = statusEnum

    var deliveryGuy: String = ""

    var packageType: PackageTypesEnum = packageType
        get() = field
        private set(value) {
            field = value
        }
    var isFragile: Boolean = isFragile
        get() = field
        private set(value) {
            field = value
        }
    var weight: Double = weight
        get() = field
        private set(value) {
            field = value
        }
    var coordinate: Coordinate = coor
        get() = field
        private set(value) {
            field = value
        }
    var name: String = name
        get() = field
        private set(value) {
            field = value
        }
    var address: String = address
        get() = field
        private set(value) {
            field = value
        }*/
}