package pt.nmplcpimenta.innowavegithuber.datamodels

import java.io.Serializable

class GHSearchUser constructor(login: String, avatarUrl: String) : Serializable {
    val login: String = login
    val avatarUrl: String = avatarUrl
}