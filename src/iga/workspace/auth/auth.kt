package iga.workspace.auth

import iga.workspace.models.Admin
import io.ktor.auth.Principal


data class UserSession(val sessionKey: String)

data class UserPrincipal(val admin: Admin) : Principal
