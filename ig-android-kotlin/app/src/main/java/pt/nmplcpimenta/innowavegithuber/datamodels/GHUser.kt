package pt.nmplcpimenta.innowavegithuber.datamodels

import java.io.Serializable

class GHUser constructor(login: String, name: String, avatarUrl: String) : Serializable {
    val login = login
    val name = name
    val avatarUrl = avatarUrl
}