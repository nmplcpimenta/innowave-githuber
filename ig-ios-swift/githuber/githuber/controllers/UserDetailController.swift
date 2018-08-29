//
//  UserDetailController.swift
//  githuber
//
//  Created by Nuno Pimenta on 29/08/2018.
//  Copyright © 2018 InnoWave. All rights reserved.
//

import UIKit

class UserDetailController: UIViewController {

    @IBOutlet weak var detailNameLabel: UILabel!
    @IBOutlet weak var detailEmailLabel: UILabel!
    @IBOutlet weak var detailAvatarImage: UIImageView!
    
    var detailGHUser: GitHubUser? {
        didSet {
            configureView()
        }
    }
    
    func configureView() {
        if let detailGHUser = detailGHUser {
            if let nameLabel = detailNameLabel, let emailLabel = detailEmailLabel {
                nameLabel.text = detailGHUser.login
                //emailLabel.text = detailGHUser.email
            }
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()

        configureView()
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
}
