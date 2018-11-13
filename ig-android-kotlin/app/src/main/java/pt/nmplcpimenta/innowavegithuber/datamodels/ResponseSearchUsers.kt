package pt.nmplcpimenta.innowavegithuber.datamodels

class ResponseSearchUsers constructor(totalCount: Int, incompleteResults: Boolean, searchResults: List<GHSearchUser>) {
    val totalCount: Int = totalCount
    val incompleteResults: Boolean = incompleteResults
    val searchResults: List<GHSearchUser> = searchResults
}