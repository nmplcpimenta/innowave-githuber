//
//  Utils.swift
//  githuber
//
//  Created by Nuno Pimenta on 01/09/2018.
//  Copyright © 2018 InnoWave. All rights reserved.
//

import Foundation
import UIKit

func executeInMainThread(blockToExecute: @escaping ()->()) {
    DispatchQueue.main.async {
        blockToExecute()
    }
}

func getErrorAlert(withTitle title: String, andMessage message: String, completion: (()->())?) -> UIAlertController {
    let alert = UIAlertController(title: title, message: message, preferredStyle: .alert)
    alert.addAction(UIAlertAction(title: "Close", style: .cancel, handler: { (uiAlertAction) in
        if completion != nil {
            completion!()
        }
    }))
    
    return alert
}
