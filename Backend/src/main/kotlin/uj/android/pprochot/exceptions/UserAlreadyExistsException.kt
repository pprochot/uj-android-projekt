package uj.android.pprochot.exceptions

class UserAlreadyExistsException(val name: String) : RuntimeException("User $name already exists.") {
}