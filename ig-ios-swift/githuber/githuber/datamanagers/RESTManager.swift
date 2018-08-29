//
//  RESTManager.swift
//  githuber
//
//  Created by Nuno Pimenta on 29/08/2018.
//  Copyright © 2018 InnoWave. All rights reserved.
//

import Foundation

class RESTManager {
    
    static func testREST(handler: @escaping (Bool, [GitHubUser]) -> Void) {
        let urlString = "https://api.github.com/users"
        guard let url = URL(string: urlString) else { return }
        
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
                handler(false, [])
                
                return
            }
            
            do {
                let ghUsers = try JSONDecoder().decode([GitHubUser].self, from: data)
                
                handler(true, ghUsers)
            } catch let jsonError {
                print(jsonError)
                
                handler(false, [])
            }
        }.resume()
    }
}
