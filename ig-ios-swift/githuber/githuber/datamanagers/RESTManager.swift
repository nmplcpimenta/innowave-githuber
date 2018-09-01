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
    static private let urlBase = "https://api.github.com/"
    
    static func searchUser(user: String, responseHandler: @escaping (GHError?, [GHSearchUser]) -> Void) {
        let urlString = urlBase+"search/users?q="+user+"+type:user+in:login+in:email"
        guard let url = URL(string: urlString) else {
            responseHandler(GHError(errMessage: "Couldn't parse URL"), [])
            
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.timeoutInterval = 30
        
        URLSession.shared.dataTask(with: request) { (data, response, error) -> Void in
            if error != nil {
                print(error!.localizedDescription)
                
                responseHandler(GHError(errMessage: "There was a communication problem: "+error!.localizedDescription), [])
                
                return
            }
            
            guard let data = data else {
                responseHandler(GHError(errMessage: "Couldn't parse data from server"), [])
                
                return
            }
            
            do {
                let ghSearchResponse = try JSONDecoder().decode(GHSearchResponse.self, from: data)
                
                responseHandler(nil, ghSearchResponse.items)
            } catch let jsonError {
                print(jsonError.localizedDescription)
                
                responseHandler(GHError(errMessage: "Couldn't parse JSON response: "+jsonError.localizedDescription), [])
            }
        }.resume()
    }
    
    static func getUser(withLogin login: String, responseHandler: @escaping (GHError?, GitHubUser?) -> Void) {
        let urlString = urlBase+"users/"+login
        guard let url = URL(string: urlString) else {
            responseHandler(GHError(errMessage: "Couldn't parse URL"), nil)
            
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.timeoutInterval = 30
        
        URLSession.shared.dataTask(with: request) { (data, response, error) -> Void in
            if error != nil {
                print(error!.localizedDescription)
                
                responseHandler(GHError(errMessage: "There was a communication problem: "+error!.localizedDescription), nil)
                
                return
            }
            
            guard let data = data else {
                responseHandler(GHError(errMessage: "Couldn't parse data"), nil)
                
                return
            }
            
            do {
                let githubUser = try JSONDecoder().decode(GitHubUser.self, from: data)
                
                responseHandler(nil, githubUser)
            } catch let jsonError {
                print(jsonError.localizedDescription)
                
                responseHandler(GHError(errMessage: "Couldn't parse JSON response: "+jsonError.localizedDescription),nil)
            }
        }.resume()
    }
    
    static func getFollowers(forLogin login: String, responseHandler: @escaping (GHError?, [GHSearchUser]) -> Void) {
        let urlString = urlBase+"users/"+login+"/followers"
        guard let url = URL(string: urlString) else {
            responseHandler(GHError(errMessage: "Couldn't parse URL"), [])
            
            return
        }
        
        var request = URLRequest(url: url)
        request.httpMethod = "GET"
        request.timeoutInterval = 30
        
        URLSession.shared.dataTask(with: request) { (data, response, error) -> Void in
            if error != nil {
                print(error!.localizedDescription)
                
                responseHandler(GHError(errMessage: "There was a communication problem: "+error!.localizedDescription), [])
                
                return
            }
            
            guard let data = data else {
                responseHandler(GHError(errMessage: "Couldn't parse data"), [])
                
                return
            }
            
            do {
                let followers = try JSONDecoder().decode([GHSearchUser].self, from: data)
                
                responseHandler(nil, followers)
            } catch let jsonError {
                print(jsonError.localizedDescription)
                
                responseHandler(GHError(errMessage: "Couldn't parse JSON response: "+jsonError.localizedDescription), [])
            }
        }.resume()
    }
}


