import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { MemeberlistComponent } from './members/memeberlist/memeberlist.component';
import { MessagesComponent } from './messages/messages.component';
import { ListsComponent } from './lists/lists.component';
import { AuthGuard } from './_guards/auth.guard';
import { MembersDetailComponent } from './members/members-detail/members-detail.component';
import { MemberDetailResolver } from './_resolvers/member-detail.resolver';
import { MemberListResolver } from './_resolvers/member-list.resolver';

export const appRoutes: Routes = [
    { path: '', component: HomeComponent},
    // { path: 'members', component: MemeberlistComponent, canActivate: [AuthGuard]}, we add guard to single route if required
    {
        path: '',
        runGuardsAndResolvers: 'always',
        canActivate: [AuthGuard],
        children: [
            { path: 'members', component: MemeberlistComponent,
                    resolve: {users: MemberListResolver}},
            { path: 'members/:id', component: MembersDetailComponent,
                    resolve: {user : MemberDetailResolver}},
            { path: 'messages', component: MessagesComponent},
            { path: 'lists', component: ListsComponent}
        ]
    },
    { path: '**', redirectTo: '', pathMatch: 'full'}
];
