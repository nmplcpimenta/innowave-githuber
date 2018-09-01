//
//  Consuela.swift
//  githuber
//
//  Created by Nuno Pimenta on 01/09/2018.
//  Copyright © 2018 InnoWave. All rights reserved.
//

import Foundation

class Consuela {
    
    static func searchUser(user: String, withCompletion completion: @escaping (GHError?, [GHSearchUser]) -> Void) {
        RESTManager.searchUser(user: user) { (ghError, searchResults) in
            executeInMainThread {
                completion(ghError, searchResults)
            }
        }
    }
    
    static func getUser(withLogin login: String, withCompletion completion: @escaping (GHError?, GitHubUser?) -> Void) {
        RESTManager.getUser(withLogin: login) { (ghError, ghUser) in
            executeInMainThread {
                completion(ghError, ghUser)
            }
        }
    }
    
    static func getFollowers(forLogin login: String, withCompletion completion: @escaping (GHError?, [GHSearchUser]) -> Void) {
        RESTManager.getFollowers(forLogin: login) { (ghError, searchResults) in
            executeInMainThread {
                completion(ghError, searchResults)
            }
        }
    }
}
