/*
*
*  Push Notifications codelab
*  Copyright 2015 Google Inc. All rights reserved.
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*      https://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License
*
*/

/* eslint-env browser, es6 */

'use strict';

const applicationServerPublicKey = 'BLrIdg4o5HbVvDA7BkNqo4iXUaj0cYr9RU8/MLmPf/czBhfSElMN5LHQKeLlHBPYI77RX2nE0B56UUn92PgnnqY=';

let isSubscribed = false;
let swRegistration = null;

function urlB64ToUint8Array(base64String) {
    const padding = '='.repeat((4 - base64String.length % 4) % 4);
    const base64 = (base64String + padding)
        .replace(/\-/g, '+')
        .replace(/_/g, '/');

    const rawData = window.atob(base64);
    const outputArray = new Uint8Array(rawData.length);

    for (let i = 0; i < rawData.length; ++i) {
        outputArray[i] = rawData.charCodeAt(i);
    }
    return outputArray;
}

function updateSubscriptionOnServer(subscription) {
    if (subscription) {
        console.log(JSON.stringify(subscription));
        var subscriptionJson = JSON.parse(JSON.stringify(subscription));
        var endpoint = subscriptionJson.endpoint;
        var p256dh = subscriptionJson.keys.p256dh;
        var auth = subscriptionJson.keys.auth;
        $.post({
            url: 'https://api.dongfg.com/graphql',
            data: JSON.stringify({"query":"mutation { savePushSubscription(subscription: {endpoint: \""+endpoint+"\", p256dh: \""+p256dh+"\", auth: \""+auth+"\"}) }","variables":null}),
            contentType: 'application/json'
        }).done(function(response) {
            console.log('SavePushSubscription:', response.data.savePushSubscription);
        });
    }
}

function subscribeUser() {
    const applicationServerKey = urlB64ToUint8Array(applicationServerPublicKey);
    swRegistration.pushManager.subscribe({
        userVisibleOnly: true,
        applicationServerKey: applicationServerKey
    })
        .then(function (subscription) {
            console.log('User is subscribed');

            updateSubscriptionOnServer(subscription);

            isSubscribed = true;
        })
        .catch(function (err) {
            console.log('Failed to subscribe the user: ', err);
        });
}

if ('serviceWorker' in navigator && 'PushManager' in window) {
    console.log('Service Worker and Push is supported');

    navigator.serviceWorker.register('/scripts/sw.js')
        .then(function (swReg) {
            console.log('Service Worker is registered', swReg);

            swRegistration = swReg;
            // Set the initial subscription value
            swRegistration.pushManager.getSubscription()
                .then(function (subscription) {
                    isSubscribed = !(subscription === null);

                    updateSubscriptionOnServer(subscription);

                    if (isSubscribed) {
                        console.log('User IS subscribed.');
                    } else {
                        console.log('User is NOT subscribed.');
                        subscribeUser();
                    }
                });
        })
        .catch(function (error) {
            console.error('Service Worker Error', error);
        });
} else {
    console.warn('Push messaging is not supported');
}
