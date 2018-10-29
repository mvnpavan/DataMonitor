using System.Collections.Generic;
using System.Threading.Tasks;
using DataMonitor.API.Models;
using Microsoft.EntityFrameworkCore;

namespace DataMonitor.API.Data
{
    public class DatingRepository : IDatingRepository
    {
        private readonly DataContext context;
        public DatingRepository(DataContext context)
        {
            this.context = context;

        }
        public void Add<T>(T entity) where T : class
        {
            context.Add(entity);
        }

        public void Delete<T>(T entity) where T : class
        {
            context.Remove(entity);
        }

        public async Task<Photo> GetPhoto(int id)
        {
            var Photo = await context.Photos.FirstOrDefaultAsync(p => p.Id == id);

            return Photo;
        }

        public async Task<User> GetUser(int id)
        {
            var user = await context.Users.Include(P => P.Photos).FirstOrDefaultAsync(u => u.Id == id);

            return user;
        }

        public async Task<IEnumerable<User>> GetUsers()
        {
            var users = await context.Users.Include(P => P.Photos).ToListAsync();

            return users;
        }

        public async Task<bool> saveAll()
        {
            return await context.SaveChangesAsync() > 0;
        }
    }
}