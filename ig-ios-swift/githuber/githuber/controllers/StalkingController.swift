//
//  StalkingController.swift
//  githuber
//
//  Created by Nuno Pimenta on 29/08/2018.
//  Copyright © 2018 InnoWave. All rights reserved.
//

import UIKit

class StalkingController: UIViewController, UITableViewDataSource, UITableViewDelegate, UISearchBarDelegate {

    @IBOutlet var tableView: UITableView!
    
    var userDetailViewController: UserDetailController? = nil
    
    var ghSearchUsers = [GHSearchUser]()
    
    let searchController = UISearchController(searchResultsController: nil)
    
    // ### Lifecycle Methods ###
    
    override func viewDidLoad() {
        super.viewDidLoad()
        
        // Do any additional setup after loading the view.
        searchController.searchBar.delegate = self
        searchController.searchBar.placeholder = "username/e-mail GitHub"
        searchController.searchBar.barStyle = .blackTranslucent
        searchController.searchBar.searchBarStyle = .minimal
        UIBarButtonItem.appearance(whenContainedInInstancesOf:[UISearchBar.self]).tintColor = UIColor.init(red: 4/256.0, green: 83/256.0, blue: 114/256.0, alpha: 1)
        
        // Include the search bar within the navigation bar.
        navigationItem.titleView = searchController.searchBar
        
        searchController.hidesNavigationBarDuringPresentation = false
        searchController.obscuresBackgroundDuringPresentation = false
        
        definesPresentationContext = true
        
        if let splitViewController = splitViewController {
            let controllers = splitViewController.viewControllers
            userDetailViewController = (controllers[controllers.count-1] as! UINavigationController).topViewController as? UserDetailController
        }
    }
    
    override func viewWillAppear(_ animated: Bool) {
        if splitViewController!.isCollapsed {
            if let selectionIndexPath = self.tableView.indexPathForSelectedRow {
                self.tableView.deselectRow(at: selectionIndexPath, animated: animated)
            }
        }
        
        if #available(iOS 11.0, *) {
            navigationItem.hidesSearchBarWhenScrolling = false
        }
        
        super.viewWillAppear(animated)
    }
    
    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        
        if #available(iOS 11.0, *) {
            navigationItem.hidesSearchBarWhenScrolling = true
        }
    }

    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    // ### TableView Methods ###
    
    func numberOfSections(in tableView: UITableView) -> Int {
        return 1
    }
    
    func tableView(_ tableView: UITableView, numberOfRowsInSection section: Int) -> Int {
        if ghSearchUsers.count > 0 {
            tableView.separatorStyle = .singleLine
            tableView.backgroundView = nil
        } else {
            let noDataLabel: UILabel     = UILabel(frame: CGRect(x: 0, y: 0, width: tableView.bounds.size.width, height: tableView.bounds.size.height))
            noDataLabel.text = "No users found"
            noDataLabel.textColor = UIColor.black
            noDataLabel.textAlignment = .center
            tableView.backgroundView = noDataLabel
            tableView.separatorStyle = .none
        }
        
        return ghSearchUsers.count
    }
    
    func tableView(_ tableView: UITableView, cellForRowAt indexPath: IndexPath) -> UITableViewCell {
        let cell = tableView.dequeueReusableCell(withIdentifier: "Cell", for: indexPath) as! GitHubUserTableViewCell
        
        let ghUser = ghSearchUsers[indexPath.row]
        
        cell.nameLabel!.text = ghUser.login
        cell.avatarImage!.downloaded(from: ghUser.avatar_url)
        
        return cell
    }
    
    func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        let sv = UIViewController.displaySpinner(onView: self.view)
        
        Consuela.getUser(withLogin: ghSearchUsers[indexPath.row].login) { (ghError, ghUser) in
            if ghError == nil {
                UIViewController.removeSpinner(spinner: sv)
                
                self.performSegue(withIdentifier: "showUserDetail", sender: ghUser)
            } else {
                UIViewController.removeSpinner(spinner: sv)
                
                // Show error
                self.present(getErrorAlert(withTitle: "Application Error", andMessage: ghError!.errMessage, completion: nil), animated: true)
            }
        }
    }
    
    // ### Navigation Methods ###
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "showUserDetail" {
            let ghUser = sender as! GitHubUser
            
            let controller = (segue.destination as! UINavigationController).topViewController as! UserDetailController
            controller.detailGHUser = ghUser
            controller.navigationItem.leftBarButtonItem = splitViewController?.displayModeButtonItem
            controller.navigationItem.leftItemsSupplementBackButton = true
        }
    }
    
    // ### UI Interaction Methods ###
    
    func searchBarIsEmpty() -> Bool {
        // Returns true if the text is empty or nil
        return searchController.searchBar.text?.isEmpty ?? true
    }
    
    func searchBarSearchButtonClicked(_ searchBar: UISearchBar) {
        filterContentForSearchText(searchController.searchBar.text!)
    }
    
    func filterContentForSearchText(_ searchText: String, scope: String = "All") {
        let sv = UIViewController.displaySpinner(onView: self.view)
        
        if !searchBarIsEmpty() {
            Consuela.searchUser(user: searchText.lowercased()) { (ghError, searchResults) in
                if (ghError == nil) {
                    self.ghSearchUsers = searchResults
                    
                    self.tableView.reloadData()
                    
                    UIViewController.removeSpinner(spinner: sv)
                } else {
                    UIViewController.removeSpinner(spinner: sv)
                    
                    // Show error
                    self.present(getErrorAlert(withTitle: "Application Error", andMessage: ghError!.errMessage, completion: nil), animated: true)
                }
            }
        } else {
            self.ghSearchUsers = []
            
            self.tableView.reloadData()
        }
    }
    
    func isFiltering() -> Bool {
        return searchController.isActive && !searchBarIsEmpty()
    }
}
