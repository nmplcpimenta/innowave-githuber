//
//  RESTManager.swift
//  githuber
//
//  Created by Nuno Pimenta on 29/08/2018.
//  Copyright © 2018 InnoWave. All rights reserved.
//

import Foundation
import UIKit

class RESTManager {
    
    static let imageCache = NSCache<NSString, UIImage>()
    
    static func searchUser(user: String, handler: @escaping (Bool, [GHSearchUser]) -> Void) {
        let urlString = "https://api.github.com/search/users?q="+user+"+type:user+in:login+in:email"
        guard let url = URL(string: urlString) else {
            handler(false, [])
            
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.timeoutInterval = 30
        
        URLSession.shared.dataTask(with: request) { (data, response, error) -> Void in
            if error != nil {
                print(error!.localizedDescription)
                
                handler(false, [])
                
                return
            }
            
            guard let data = data else {
                print("data stuff")
                
                handler(false, [])
                
                return
            }
            
            do {
                let ghSearchResponse = try JSONDecoder().decode(GHSearchResponse.self, from: data)
                
                handler(true, ghSearchResponse.items)
            } catch let jsonError {
                print(jsonError)
                
                handler(false, [])
            }
        }.resume()
    }
    
    static func getUser(withLogin login: String, handler: @escaping (Bool, GitHubUser?) -> Void) {
        let urlString = "https://api.github.com/users/"+login
        
        guard let url = URL(string: urlString) else {
            handler(false, nil)
            
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.timeoutInterval = 30
        
        URLSession.shared.dataTask(with: request) { (data, response, error) -> Void in
            if error != nil {
                print(error!.localizedDescription)
                
                handler(false, nil)
                
                return
            }
            
            guard let data = data else {
                print("data stuff")
                
                handler(false, nil)
                
                return
            }
            
            do {
                let githubUser = try JSONDecoder().decode(GitHubUser.self, from: data)
                
                handler(true, githubUser)
            } catch let jsonError {
                print(jsonError)
                
                handler(false,nil)
            }
        }.resume()
    }
    
    static func getFollowers(forLogin login: String, handler: @escaping (Bool, [GHSearchUser]) -> Void) {
        let urlString = "https://api.github.com/users/"+login+"/followers"
        
        guard let url = URL(string: urlString) else {
            handler(false, [])
            
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.timeoutInterval = 30
        
        URLSession.shared.dataTask(with: request) { (data, response, error) -> Void in
            if error != nil {
                print(error!.localizedDescription)
                
                handler(false, [])
                
                return
            }
            
            guard let data = data else {
                print("data stuff")
                
                handler(false, [])
                
                return
            }
            
            do {
                let followers = try JSONDecoder().decode([GHSearchUser].self, from: data)
                
                handler(true, followers)
            } catch let jsonError {
                print(jsonError)
                
                handler(false, [])
            }
        }.resume()
    }
}


