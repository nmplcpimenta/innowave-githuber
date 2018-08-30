//
//  GHSearchResponse.swift
//  githuber
//
//  Created by Nuno Pimenta on 30/08/2018.
//  Copyright © 2018 InnoWave. All rights reserved.
//

import Foundation

struct GHSearchResponse : Codable {
    let total_count : Int
    let incomplete_results : Bool
    let items : [GHSearchUser]
}
