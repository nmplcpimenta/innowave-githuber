//
//  UserDetailController.swift
//  githuber
//
//  Created by Nuno Pimenta on 29/08/2018.
//  Copyright © 2018 InnoWave. All rights reserved.
//

import UIKit

class UserDetailController: UIViewController, UITableViewDataSource, UITableViewDelegate {
    
    @IBOutlet weak var detailNameLabel: UILabel!
    @IBOutlet weak var detailEmailLabel: UILabel!
    @IBOutlet weak var detailAvatarImage: UIImageView!
    
    @IBOutlet weak var containerViewNotFound: UIView!
    
    @IBOutlet weak var followersTableView: UITableView!
    
    var followers = [GHSearchUser]()
    
    var detailGHUser: GitHubUser? {
        didSet {
            configureView()
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        containerViewNotFound.isHidden = detailGHUser != nil
        
        if detailGHUser != nil {
            self.navigationController?.navigationBar.tintColor = UIColor.white
        } else {
            self.navigationController?.navigationBar.tintColor = UIColor.init(red: 4/256.0, green: 83/256.0, blue: 114/256.0, alpha: 1)
        }
    }
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        if detailGHUser != nil {
            configureView()
            
            refreshFollowers()
        }
    }
    
    func configureView() {
        if let detailGHUser = detailGHUser {
            if let nameLabel = detailNameLabel, let emailLabel = detailEmailLabel, let avatarImage = detailAvatarImage {
                nameLabel.text = detailGHUser.name != "" ? detailGHUser.name : "..."
                emailLabel.text = "@"+detailGHUser.login
                avatarImage.downloaded(from: detailGHUser.avatar_url)
            }
        }
    }
    
    func refreshFollowers() {
        RESTManager.getFollowers(forLogin: detailGHUser!.login) { (wasSuccessful, followersResponse) in
            if wasSuccessful {
                self.followers = followersResponse
                
                DispatchQueue.main.async {
                    self.followersTableView.reloadData()
                }
            } else {
                // TODO Deal with error
            }
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if self.followers.count > 0 {
            tableView.separatorStyle = .singleLine
            tableView.backgroundView = nil
        } else {
            let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
            noDataLabel.text = "No followers"
            noDataLabel.textColor = UIColor.black
            noDataLabel.textAlignment = .center
            tableView.backgroundView = noDataLabel
            tableView.separatorStyle = .none
        }
        
        return self.followers.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! GitHubUserTableViewCell
        
        let follower = self.followers[indexPath.row]
        
        cell.nameLabel!.text = follower.login
        cell.avatarImage!.downloaded(from: follower.avatar_url)
        
        return cell
    }
}
