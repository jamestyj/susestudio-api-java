/*
 * Copyright (c) 2010 Novell Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.suse.studio.api;

/*
 * - Appliances
 *   - get: appliances
 *     - get: id, name, last_edited, edit_url, icon_url, base system, parent (id, name),
 *            builds (id, version, image_type, image_size, compressed_image_size,
 *                    download_url),
 *            status (id, state, issues [ type, text, solution <type, package> ])
 *     - post: clone appliance (appliance id, name, arch)
 *     - delete: appliance id         
 *       
 *     Studio studio = new Studio("user_name", "api_key");
 *     appliances = studio.getAppliances();
 *     
 *     new_appliance = appliance.clone();
 *     appliance.delete();
 *     
 * 	 - Repositories
 *     - get(app id):
 *       - repo: id, name, type, base system, base url, matches (name, base url) 
 *     - put(app id): ??
 *     - post(app id, repo id): add repo 
 *     - post(app id, repo id): remove repo
 *     - post(app id): add user repo
 *     
 *    appliance.getRepositories();
 *    appliance.addRepository(repo_id);
 *    appliance.removeRepository(repo_id);
 *    appliance.addUserRepository(repo_id); ?
 *     
 * 	 - Software
 *     - get(app id): packages (version)
 *     - put(app id): ??
 *     - get(app id, build id): installed
 *       - repo (software, packages, versions)
 *     - post: add package
 *     - post: remove package
 *     - post: add pattern
 *     - post: remove pattern
 *     - post: ban package
 *     - post: unban package
 *     - get: software search
 *     
 *   appliance.getSoftware();
 *   build.getSoftware();
 *   appliance.addPackage();
 *   appliance.removePackage();
 *   appliance.addPattern();
 *   appliance.removePattern();
 *   appliance.banPackage();
 *   appliance.unbanPackage();
 *   appliance.findSoftware();
 *     
 * 	 - Image files     
 *     - get: image files (download file)
 *  
 *   appliance.getDownloads();
 *     
 *   - GPG keys     
 *     - get: list all gpg keys
 *     - post: add gpg key
 *     - delete: delete gpg key
 *     
 *   appliance.getGpgKeys();
 *   appliance.addGpgKey();
 *   appliance.removeGpgKey(); or gpgKey.delete();
 *     
 * - Overlay files
 *   - get: list of files (id, filename, path, owner, group, perms, enabled, download url)
 *   - post: upload file
 *   
 *   appliance.getOverlayFiles();
 *   appliance.uploadOverlayFile();
 *   
 * - Running builds
 * - Finished builds
 * - RPM uploads
 * - Repositories
 */

/*
 * TODO unit tests
 * TODO full set of API
 * TODO documentation
 * TODO command line client
 */
/**
 * 
 * @author James Tan <james@jam.sg>
 */
public class Main {

	public static void main(String[] args) throws Exception {

		Studio studio = new Studio();

//		Appliances appliances = studio.getAppliances();
		
//		Account account = studio.getAccount();
//		System.out.println(account);
		
//		TemplateSets ts = studio.getTemplateSets();
//		System.out.println(ts.toString());
		
	}
}
