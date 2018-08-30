//
//  GitHubUser.swift
//  githuber
//
//  Created by Nuno Pimenta on 29/08/2018.
//  Copyright © 2018 InnoWave. All rights reserved.
//

import UIKit

struct GitHubUser : Codable {
    let login: String
    let avatar_url: String
    let name: String
    let email: String
    
    init(from decoder: Decoder) throws {
        let container = try decoder.container(keyedBy: CodingKeys.self)
        login = try container.decode(String.self, forKey: .login)
        avatar_url = try container.decode(String.self, forKey: .avatar_url)
        name = try container.decode(String.self, forKey: .name)
        do { email = try container.decode(String.self, forKey: .email) }
        catch { email = "" }
    }
}
